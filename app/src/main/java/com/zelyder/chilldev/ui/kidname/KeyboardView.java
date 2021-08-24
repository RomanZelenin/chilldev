package com.zelyder.chilldev.ui.kidname;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yandex.tv.common.ui.Fonts;
import com.yandex.tv.common.ui.FontsKt;
import com.zelyder.chilldev.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Keyboard class that you can use to enter into any text field.
 */
public class KeyboardView extends RecyclerView {

    private static final String TAG = "Keyboard";

    /**
     * Languages supported by keyboard
     */
    private static final String[] AVAILABLE_LANGUAGES = {"ru", "en", "uk", "tr", "num"};

    private int columnCount = 6;
    private XmlPullParser inputXml;

    private ButtonViewFactory factory;
    private AdapterController controller;
    private SearchNextFocusListener focusListener;
    private View deleteButton;
    private int awaitingFocus = KeyButton.EMPTY;

    @VisibleForTesting
    KeyboardAdapter adapter;
    @VisibleForTesting
    KeyboardLayoutManager manager;

    /**
     * Set keyboard languages
     *
     * @param inputXml xml that contains information about languages
     */
    public void setInputXml(XmlPullParser inputXml) {
        this.inputXml = inputXml;
        controller.parseXml();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Set the languages used for the keyboard.
     * The flag has the following representation:
     * 1. languages translate to binary
     * 2. if the i-th byte == 1, then {@link #AVAILABLE_LANGUAGES}[i] is used
     *
     * @param languages languages flag
     */
    public void setLanguages(int languages) {
        if (inputXml != null && adapter != null) {
            controller.setAvailableLangs(languages);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * @param selector selector of keyboard button
     */
    public void setKeySelector(Drawable selector) {
        if (selector != null)
            factory.setKeySelector(selector);
    }

    /**
     * @param color text color of keyboard button
     */
    public void setTextColor(ColorStateList color) {
        if (color != null)
            factory.setTextColor(color);
    }

    /**
     * @param animator animator of keyboard button
     */
    public void setKeyAnimator(int animator) {
        factory.setKeyAnimator(animator);
    }

    /**
     * @param size text size of keyboard button
     */
    public void setTextSize(int size) {
        if (size > 0)
            factory.setTextSize(size);
    }

    /**
     * Interface definition for a callbacks to be invoked when a user interacts with keyboard.
     */
    public interface KeyboardListener {
        /**
         * Called when a user press a text button
         *
         * @param symbol The symbol which was pressed
         */
        void onInput(Character symbol);

        /**
         * Called when a user press a delete button
         */
        void onDelete();

        /**
         * Called when a user long press a delete button
         */
        void onDeleteAll();

        /**
         * Called when a user press a enter button
         */
        void onEnter();
    }

    /**
     * Bind a listener with a keyboard
     *
     * @param listener the listener to bind the data to
     */
    public void bindInput(KeyboardListener listener) {
        adapter = new KeyboardAdapter(factory, controller);
        adapter.setClickListener(listener);
        controller.bindGridManager(manager);
        setAdapter(adapter);
    }

    public void unbindInput() {
        if (adapter != null) {
            adapter.setClickListener(null);
        }
        adapter = null;
        setAdapter(null);
    }

    public void requestDeleteFocus() {
        if (deleteButton != null) {
            deleteButton.requestFocus();
        } else {
            awaitingFocus = KeyButton.DELETE;
        }
    }

    /**
     * Bind a focus listener with a keyboard
     *
     * @param focusListener the listener of focus
     */
    public void setKeyboardNextFocusListener(SearchNextFocusListener focusListener) {
        this.focusListener = focusListener;
    }

    /**
     * Create a keyboard view and set a global view parameters of button
     *
     * @param context context of application
     * @param attrs   attributes for keyboard view and buttons view
     */
    public KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = null;
        controller = new AdapterController();
        factory = new ButtonViewFactory();
        try {
            attributes = context.obtainStyledAttributes(attrs, R.styleable.KeyboardView);
            factory.setKeySelector(attributes.getDrawable(R.styleable.KeyboardView_keyBackgroundSelector));
            factory.setTextColor(attributes.getColorStateList(R.styleable.KeyboardView_keyColorSelector));
            factory.setKeyAnimator(attributes.getResourceId(R.styleable.KeyboardView_keyAnimator, 0));
            factory.setTextSize(attributes.getDimension(R.styleable.KeyboardView_keyTextSize, 15));
            columnCount = attributes.getInteger(R.styleable.KeyboardView_columnCount, columnCount);
            controller.setAvailableLangs(attributes.getInteger(R.styleable.KeyboardView_languages, 18));
            this.setBackground(attributes.getDrawable(R.styleable.KeyboardView_keyboardBackground));
        } finally {
            if (attributes != null)
                attributes.recycle();
        }

        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.search_key_height) * 3);

        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        manager = new KeyboardLayoutManager(getContext(), columnCount);
        setLayoutManager(manager);
    }

    private class KeyboardLayoutManager extends GridLayoutManager {

        KeyboardLayoutManager(Context context, int columnCount) {
            super(context, columnCount);
        }

        @Nullable
        @Override
        public View onInterceptFocusSearch(@NonNull View focused, int direction) {
            int lastColumnIndex = columnCount - 1;
            int indexInColumn = getChildAdapterPosition(focused) % lastColumnIndex;

            if (indexInColumn > 0 && indexInColumn < lastColumnIndex - 1) {
                switch (direction) {
                    case View.FOCUS_LEFT:
                    case View.FOCUS_RIGHT:
                        return findNextFocusableKey(focused, direction);
                }
            }

            return super.onInterceptFocusSearch(focused, direction);
        }

        private View findNextFocusableKey(View focused, int direction) {
            View current = focused;
            int step;
            switch (direction) {
                case View.FOCUS_LEFT:
                    step = -1;
                    break;
                case View.FOCUS_RIGHT:
                    step = 1;
                    break;
                default:
                    return null;
            }
            while (true) {
                current = getChildAt(getChildAdapterPosition(current) + step);
                if (current == null || current.isFocusable()) {
                    return current;
                }
            }
        }
    }

    @Override
    public View focusSearch(View focused, int direction) {
        if (focusListener == null) {
            return super.focusSearch(focused, direction);
        }

        int itemInColumn = columnCount - 1;
        switch (direction) {
            case View.FOCUS_UP:
                if (getChildAdapterPosition(focused) / itemInColumn == 0) {
                    return focusListener.searchTop(focused);
                }
                break;
            case View.FOCUS_DOWN:
                if (getChildAdapterPosition(focused) / itemInColumn
                        == getAdapter().getItemCount() / itemInColumn -
                        (getAdapter().getItemCount() % itemInColumn == 0 ? 1 : 0)) {
                    return focusListener.searchDown(focused);
                }
        }

        return super.focusSearch(focused, direction);
    }

    class AdapterController {

        private int prevLangIndex = 0;
        private int currentLangIndex = 0;
        private List<String> availableLangs = new ArrayList<>();
        private HashMap<String, List<KeyButton>> alphabetsMap;

        /**
         * Get languages information from xml
         */
        private void parseXml() {
            if (inputXml == null)
                throw new NullPointerException("Can't find xml file!");

            currentLangIndex = 0;
            try {
                KeyboardXmlParser parser = new KeyboardXmlParser(inputXml);
                alphabetsMap = parser.parse();
            } catch (IOException | XmlPullParserException ex) {
            }
        }

        /**
         * Change available languages
         *
         * @param languages languages that keyboard uses
         */
        private void setAvailableLangs(int languages) {
            currentLangIndex = 0;
            availableLangs.clear();
            for (int i = 0; i < AVAILABLE_LANGUAGES.length; i++)
                if ((languages & ((int) Math.pow(2, i))) != 0) {
                    availableLangs.add(AVAILABLE_LANGUAGES[i]);
                }
        }

        /**
         * Update size of buttons
         *
         * @param manager manager that controls the size of buttons
         */
        @VisibleForTesting
        void bindGridManager(GridLayoutManager manager) {
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int size = getButtons().get(position).getSpanSize();
                    return size > 0 ? size : columnCount;
                }
            });
        }

        /**
         * Returns current list of buttons
         */
        List<KeyButton> getButtons() {
            if (alphabetsMap == null)
                parseXml();

            return alphabetsMap.get(availableLangs.get(currentLangIndex));
        }

        /**
         * Change current language to numbers or numbers to language
         */
        List<KeyButton> nextLang() {
            if (availableLangs.size() > 1)
                currentLangIndex = (currentLangIndex + 1) % (availableLangs.size() - 1);

            return getButtons();
        }

        /**
         * Change current language to another
         */
        List<KeyButton> changeNum() {
            if (currentLangIndex == availableLangs.size() - 1) {
                currentLangIndex = prevLangIndex;
            } else {
                prevLangIndex = currentLangIndex;
                currentLangIndex = availableLangs.size() - 1;
            }

            return getButtons();
        }
    }


    /**
     * Adapter that fills the keyboard with buttons.
     */
    @VisibleForTesting
    class KeyboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private KeyboardListener itemClickListener;
        private ButtonViewFactory factory;
        private AdapterController controller;

        /**
         * @param factory    button factory that creates views of buttons
         * @param controller list of used alphabets
         */
        KeyboardAdapter(ButtonViewFactory factory, AdapterController controller) {
            this.factory = factory;
            this.controller = controller;
            this.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                }
            });
        }

        void setClickListener(KeyboardListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public int getItemCount() {
            return controller.getButtons() != null ? controller.getButtons().size() : 0;
        }

        @Override
        public int getItemViewType(int position) {
            return controller.getButtons().get(position).type;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            View view = factory.createTextButton(context);

            switch (viewType) {
                case KeyButton.TEXT:
                    return new TextViewHolder(view);
                case KeyButton.DELETE:
                    view = factory.createImageButton(
                            context,
                            context.getResources().getDrawable(R.drawable.ic_delete, context.getTheme())
                    );
                    deleteButton = view;
                    return new DeleteViewHolder(view);
                case KeyButton.LANG:
                    view = factory.createImageButton(
                            context,
                            context.getResources().getDrawable(R.drawable.ic_globe, context.getTheme())
                    );
                    return new LangViewHolder(view);
                case KeyButton.NUM:
                    return new NumViewHolder(view);
                case KeyButton.ENTER:
                    return new EnterViewHolder(view);
                case KeyButton.SPACE:
                    view = factory.createImageButton(
                            context,
                            context.getResources().getDrawable(R.drawable.ic_space, context.getTheme()),
                            ImageView.ScaleType.CENTER_CROP
                    );
                    return new SpaceViewHolder(view);
                default:
                    return new EmptyViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            KeyButton button = controller.getButtons().get(position);

            holder.itemView.setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                    if(v.getTag(v.getId()) == null) {
                        v.setTag(v.getId(),v);
                    }else {
                        v.setTag(v.getId(), null);
                        v.animate()
                        .scaleX(1.3f)
                                .scaleY(1.3f)
                                .start();
                    }
                }
                return false;
            });

            holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    holder.itemView.animate()
                            .scaleX(1.3f)
                            .scaleY(1.3f)
                            .start();
                } else {
                    holder.itemView.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .start();
                }
            });

            switch (awaitingFocus) {
                case KeyButton.DELETE:
                    if (holder instanceof DeleteViewHolder) {
                        holder.itemView.requestFocus();
                        awaitingFocus = KeyButton.EMPTY;
                    }
                    break;
                case KeyButton.NUM:
                    if (holder instanceof NumViewHolder) {
                        holder.itemView.requestFocus();
                        awaitingFocus = KeyButton.EMPTY;
                    }
                    break;
                case KeyButton.LANG:
                    if (holder instanceof LangViewHolder) {
                        holder.itemView.requestFocus();
                        awaitingFocus = KeyButton.EMPTY;
                    }
                    break;
                default:
                    break;
            }

            if (holder.itemView instanceof TextView) {
                ((TextView) holder.itemView).setText(button.getLabel());
            }

            int width = holder.itemView.getResources().getDimensionPixelSize(R.dimen.search_key_width);
            int height = holder.itemView.getResources().getDimensionPixelSize(R.dimen.search_key_height);

            if (holder instanceof OnClickListener) {
                holder.itemView.setOnClickListener((OnClickListener) holder);
            }
            if (holder instanceof OnLongClickListener) {
                holder.itemView.setOnLongClickListener((OnLongClickListener) holder);
            }

            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(width * button.spanSize, height));
        }

        @Override
        public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
            holder.itemView.setOnClickListener(null);
            holder.itemView.setOnLongClickListener(null);
        }

        /**
         * Change current language to another
         */
        @VisibleForTesting
        void changeLang() {
            controller.nextLang();
            notifyDataSetChanged();
        }

        /**
         * Change current language to numbers or numbers to language
         */
        @VisibleForTesting
        void changeNum() {
            controller.changeNum();
            notifyDataSetChanged();
        }

        /**
         * Class of empty keyboard button view holder
         */
        public class EmptyViewHolder extends RecyclerView.ViewHolder {

            EmptyViewHolder(View itemView) {
                super(itemView);
                itemView.setFocusable(false);
                itemView.setFocusableInTouchMode(false);
            }
        }

        /**
         * Class of keyboard delete button view holder
         */
        public class DeleteViewHolder extends RecyclerView.ViewHolder implements OnLongClickListener,
                OnClickListener {

            DeleteViewHolder(View itemView) {
                super(itemView);
                itemView.setFocusable(true);
            }

            @Override
            public void onClick(View view) {
                if (itemClickListener != null)
                    itemClickListener.onDelete();
            }

            @Override
            public boolean onLongClick(View v) {
                KeyButton button = controller.getButtons().get(getAdapterPosition());
                if (button.type == KeyButton.DELETE)
                    itemClickListener.onDeleteAll();
                return false;
            }
        }

        /**
         * Class of keyboard text button view holder
         */
        public class TextViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

            TextViewHolder(View itemView) {
                super(itemView);
                itemView.setFocusable(true);
            }

            @Override
            public void onClick(View view) {
                KeyButton button = controller.getButtons().get(getAdapterPosition());
                if (itemClickListener != null)
                    itemClickListener.onInput(button.getLabel().charAt(0));
            }
        }

        /**
         * Class of keyboard change num button view holder
         */
        public class NumViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

            NumViewHolder(View itemView) {
                super(itemView);
                itemView.setFocusable(true);
            }

            @Override
            public void onClick(View view) {
                changeNum();
                awaitingFocus = KeyButton.NUM;
            }
        }

        /**
         * Class of keyboard change language button view holder
         */
        public class LangViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

            LangViewHolder(View itemView) {
                super(itemView);
                itemView.setFocusable(true);
            }

            @Override
            public void onClick(View view) {
                changeLang();
                awaitingFocus = KeyButton.LANG;
            }
        }

        /**
         * Class of keyboard enter button view holder
         */
        public class EnterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

            EnterViewHolder(View itemView) {
                super(itemView);
                itemView.setFocusable(true);
            }

            @Override
            public void onClick(View view) {
                if (itemClickListener != null)
                    itemClickListener.onEnter();
            }
        }

        /**
         * Class of keyboard space button view holder
         */
        public class SpaceViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

            SpaceViewHolder(View itemView) {
                super(itemView);
                itemView.setFocusable(true);
            }

            @Override
            public void onClick(View view) {
                if (itemClickListener != null)
                    itemClickListener.onInput(' ');
            }
        }
    }

    /**
     * Class of keyboard button. This class contains the main information of button.
     */
    static class KeyButton {

        static final int TEXT = 0, DELETE = 1, ENTER = 2, LANG = 3, NUM = 4, SPACE = 5, EMPTY = 6;

        private int type, spanSize = 1;
        private String label = "";

        public void setLabel(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public void setType(int type) {
            this.type = type;
            setSpanSize();
        }

        public int getType() {
            return type;
        }

        public int getSpanSize() {
            return spanSize;
        }

        public void setSpanSize(int spanSize) {
            if (spanSize < 0)
                throw new IllegalArgumentException("button size is always > 0");
            this.spanSize = spanSize;
        }

        public void setSpanSize() {
            switch (type) {
                case DELETE:
                case LANG:
                case NUM:
                    spanSize = 2;
                    return;
                case TEXT:
                case SPACE:
                case ENTER:
                case EMPTY:
                default:
                    spanSize = 1;
            }
        }

        public KeyButton() {
            type = EMPTY;
        }
    }


    /**
     * Class that creates the view of buttons depending on the type
     */
    private static class ButtonViewFactory {

        private Drawable keySelector;
        private ColorStateList textColor;
        private String textFontFamily = Typeface.DEFAULT.toString();
        private int keyAnimator, textFontStyle;
        private float textSize;

        void setKeySelector(Drawable keySelector) {
            this.keySelector = keySelector;
        }

        void setTextColor(ColorStateList textColor) {
            this.textColor = textColor;
        }

        void setKeyAnimator(int keyAnimator) {
            this.keyAnimator = keyAnimator;
        }

        void setTextSize(float textSize) {
            this.textSize = textSize;
        }

        /**
         * Create the view of text button
         *
         * @param context context of activity
         * @return text button view
         */
        View createTextButton(Context context) {
            AppCompatTextView view = new AppCompatTextView(context);
            view.setGravity(Gravity.CENTER);
            view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            FontsKt.applyFont(view, Fonts.yandexSansMedium(context));
            if (textColor != null)
                view.setTextColor(textColor);
            Drawable background = keySelector;
            if (background != null)
                view.setBackground(background.getConstantState().newDrawable());
            if (keyAnimator != 0) {
                StateListAnimator anim = AnimatorInflater.loadStateListAnimator(context, keyAnimator);
                view.setStateListAnimator(anim);
            }
            view.setFocusable(true);

            return view;
        }

        /**
         * Create the view of image button
         *
         * @param context context of activity
         * @param image   image of button
         * @return image button view
         */
        View createImageButton(Context context, Drawable image) {
            return createImageButton(context, image, ImageView.ScaleType.CENTER);
        }

        /**
         * Create the view of image button
         *
         * @param context   context of activity
         * @param image     image of button
         * @param scaleType image scale type
         * @return image button view
         */
        View createImageButton(Context context, Drawable image, ImageView.ScaleType scaleType) {
            ImageView view = new ImageView(context);
            view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            view.setImageTintList(textColor);
            view.setImageDrawable(image);
            view.setPadding(0, 10, 0, 10);
            view.setScaleType(scaleType);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            Drawable background = keySelector;
            if (background != null)
                view.setBackground(background.getConstantState().newDrawable());
            if (keyAnimator != 0) {


                StateListAnimator anim = AnimatorInflater.loadStateListAnimator(context, keyAnimator);
                view.setStateListAnimator(anim);
            }
            view.setFocusable(true);

            return view;
        }
    }


    /**
     * Class that parse the xml file into HashMap of alphabets
     */
    static class KeyboardXmlParser {
        // We don't use namespaces
        private static final String ns = null;
        private HashMap<String, List<KeyButton>> alphabetsMap = new HashMap<>();
        private XmlPullParser parser;

        /**
         * Simple constructor
         *
         * @param parser parser for the file of the following form:
         *               <input-methods>
         *               <input lang="{alphabet language, e.g. ru}">
         *               <item type="{button type}" size="{size of this button}" />
         *               ...
         *               </input>
         *               ...
         *               </input-method>
         * @see KeyButton
         */
        KeyboardXmlParser(XmlPullParser parser) {
            this.parser = parser;
        }

        /**
         * Create a HashMap from xml that contains language information
         *
         * @return HashMap of alphabets
         * @throws XmlPullParserException if format of xml is wrong
         * @throws IOException            if format of xml is wrong
         */
        public HashMap<String, List<KeyButton>> parse() throws XmlPullParserException, IOException {
            if (parser == null)
                throw new XmlPullParserException("Parser is null");

            // Skip <?xml ... ?> tag
            parser.nextTag();
            // Skip <input-methods> tag
            parser.nextTag();

            // Now parse
            while (!parser.getName().equals("input-methods")) {
                readInput();
            }

            return alphabetsMap;
        }

        private void readInput() throws XmlPullParserException, IOException {
            List<KeyButton> alphabet = new ArrayList<>();
            // START_TAG
            String lang = parser.getAttributeValue(ns, "lang");
            parser.nextTag();
            // BODY
            while (parser.getEventType() != XmlPullParser.END_TAG || !parser.getName().equals("input")) {
                alphabet.add(readItem());
            }
            // END_TAG
            parser.nextTag();

            alphabetsMap.put(lang, alphabet);
        }

        private KeyButton readItem() throws IOException, XmlPullParserException {
            // START_TAG
            KeyButton key = new KeyButton();
            try {
                key.setType(Integer.parseInt(parser.getAttributeValue(ns, "type")));
                String size = parser.getAttributeValue(ns, "size");
                if (size != null) {
                    key.setSpanSize(Integer.parseInt(size));
                }
            } catch (NumberFormatException ex) {
                throw new XmlPullParserException("Wrong attribute in Line " + parser.getLineNumber());
            }
            parser.next();
            // BODY
            if (parser.getEventType() == XmlPullParser.TEXT) {
                key.setLabel(parser.getText());
                parser.next();
            }
            // END_TAG
            parser.nextTag();

            return key;
        }
    }
}