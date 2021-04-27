package by.itacademy.familywallet.view.fragment

import android.os.Bundle
import android.view.View
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentUsersSettingsBinding
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.utils.UserUtils
import by.itacademy.familywallet.view.BaseFragment
import by.itacademy.familywallet.viewmodel.UserSettingsViewModel
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel

class UsersSettingsFragment : BaseFragment<FragmentAdapter, UserSettingsViewModel>(R.layout.fragment_users_settings) {
    private lateinit var binding: FragmentUsersSettingsBinding

    override val viewModel by viewModel<UserSettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersSettingsBinding.bind(view)
        showActionBar(false)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            saveButton.setOnClickListener {
                createPartner()
            }
            uid.text = UserUtils.getUsersUid()
            title.text = String.format(getString(R.string.uid), UserUtils.getUserName())
            Glide.with(userPhoto)
                .load(UserUtils.getUserPhoto())
                .circleCrop()
                .into(userPhoto)
        }
    }

    private fun createPartner() {
        val text = binding.itemName.text.toString()
        if (!text.isNullOrEmpty() && text != UserUtils.getUsersUid()) {
            viewModel.createPartner(text)
            onBack()
        } else {
            dialog.createNegativeDialog(context!!, getString(R.string.alert_negative_message_category_create))
        }
    }

    companion object {
        fun newInstance() = UsersSettingsFragment()
    }

}