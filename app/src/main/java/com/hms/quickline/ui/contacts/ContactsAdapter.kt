package com.hms.quickline.ui.contacts

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hms.quickline.R
import com.hms.quickline.core.util.getStatus
import com.hms.quickline.core.util.layoutInflaterFactory
import com.hms.quickline.data.model.Users
import com.hms.quickline.databinding.CardCallDialogBinding

class ContactsAdapter(list: ArrayList<Users>, listener: ICallDialogAdapter) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var itemList: ArrayList<Users> = list
    private var itemListener = listener
    private lateinit var binding: CardCallDialogBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CardCallDialogBinding.inflate(parent.layoutInflaterFactory(), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(itemList[position])

    inner class ViewHolder(binding: CardCallDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: Users) {

            with(binding) {

                userData = user

                if (user.isAvailable) {

                    user.lastSeen?.let {
                        when (getStatus(it)) {

                            root.context.getString(R.string.available) -> {
                                tvState.text = root.context.getString(R.string.available)
                                imgStateAvailable.backgroundTintList = ContextCompat.getColorStateList(root.context, R.color.available_color)
                                imgStateAvailable.setImageResource(R.drawable.ic_check_12)
                            }

                            root.context.getString(R.string.away) -> {
                                tvState.text = root.context.getString(R.string.away)
                                imgStateAvailable.backgroundTintList = ContextCompat.getColorStateList(root.context, R.color.away_color)
                                imgStateAvailable.setImageResource(R.drawable.ic_recent)
                            }

                            root.context.getString(R.string.offline) -> {
                                tvState.text = root.context.getString(R.string.offline)
                                imgStateAvailable.backgroundTintList = ContextCompat.getColorStateList(root.context, R.color.offline_color)
                                imgStateAvailable.setImageResource(0)

                            }
                        }
                    } ?: run {
                        tvState.text = root.context.getString(R.string.available)
                        imgStateAvailable.backgroundTintList = ContextCompat.getColorStateList(root.context, R.color.available_color)
                        imgStateAvailable.setImageResource(R.drawable.ic_check_12)
                    }

                } else {
                    tvState.text = root.context.getString(R.string.busy)
                    imgStateAvailable.backgroundTintList = ContextCompat.getColorStateList(root.context, R.color.busy_color)
                    imgStateAvailable.setImageResource(R.drawable.ic_phone_24)
                }

                imgVoiceCall.setOnClickListener {
                    itemListener.onItemSelected(true, user)
                }

                imgVideoCall.setOnClickListener {
                    itemListener.onItemSelected(false, user)
                }

            }
        }
    }

    override fun getItemCount() = itemList.size

    interface ICallDialogAdapter {
        fun onItemSelected(isVoiceCall: Boolean, user: Users)
    }
}