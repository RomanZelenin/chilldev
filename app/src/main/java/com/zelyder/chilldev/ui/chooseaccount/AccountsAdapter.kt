package com.zelyder.chilldev.ui.chooseaccount

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zelyder.chilldev.R
import com.zelyder.chilldev.customview.AccountView
import com.zelyder.chilldev.customview.AddProfileView
import com.zelyder.chilldev.domain.models.Account
import com.zelyder.chilldev.domain.models.KidIcon
import de.hdodenhof.circleimageview.CircleImageView

class AccountsAdapter(
    private val context: Context,
    private val listener: AccountClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var accounts = listOf<Account>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ADD_PROFILE_TYPE -> {
                val itemView = AddProfileView(context, null)
                return AddProfileViewHolder(itemView)
            }
            ACCOUNT_TYPE -> {
                val itemView = AccountView(context, null)
                return AccountViewHolder(itemView)
            }
        }
        throw IllegalArgumentException("Type not found")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AddProfileViewHolder -> holder.bind(listener)
            is AccountViewHolder -> holder.bind(accounts[position], listener)
        }
    }

    override fun getItemCount(): Int {
        return accounts.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == accounts.size)
            return ADD_PROFILE_TYPE
        return ACCOUNT_TYPE
    }

    companion object {
        const val ACCOUNT_TYPE = 0
        const val ADD_PROFILE_TYPE = 1
    }

    class AccountViewHolder(
        itemView: AccountView
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(account: Account, listener: AccountClickListener) {
            val view = itemView as AccountView
            view.text = account.name
            view.checked = account.checked
            val civAvatar = view.findViewById<CircleImageView>(R.id.ivAvatar)
            if (account.avatar.isDigitsOnly()) {
                if (account.avatar.toInt() == 0) {
                    civAvatar.setImageDrawable(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_adult
                        )
                    )
                } else {
                    civAvatar.setImageDrawable(
                        ContextCompat.getDrawable(
                            itemView.context,
                            KidIcon.getForPosition(account.avatar.toInt()).resId
                        )
                    )
                }
            } else {
                Picasso.get()
                    .load("https://avatars.yandex.net/get-yapic/${account.avatar}/islands-200")
                    .into(civAvatar)
            }

            if (absoluteAdapterPosition == 0) {
                view.setOnClickListener {
                    listener.onParentAccountClick()
                }
            }
        }
    }

    class AddProfileViewHolder(
        itemView: AddProfileView
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(listener: AccountClickListener) {
            val view = itemView as AddProfileView
            view.setOnClickListener {
                listener.onAddProfileClick()
            }
        }
    }
}