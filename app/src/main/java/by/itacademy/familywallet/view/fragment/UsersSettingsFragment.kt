package by.itacademy.familywallet.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.databinding.FragmentUsersSettingsBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.presentation.FragmentAdapter
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.UserUtils
import by.itacademy.familywallet.view.BaseFragment
import by.itacademy.familywallet.view.activity.FragmentsActivity
import by.itacademy.familywallet.viewmodel.BaseViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class UsersSettingsFragment : BaseFragment<FragmentAdapter,BaseViewModel>(R.layout.fragment_users_settings) {
    private lateinit var binding: FragmentUsersSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersSettingsBinding.bind(view)
        showActionBar(false)
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
        CoroutineScope(Dispatchers.IO).launch {
            if (!text.isNullOrEmpty() && text != UserUtils.getUsersUid()) {
                val nullPartner = repo.getPartner()
                repo.deleteItem(nullPartner)
                repo.addPartner(
                    UIModel.AccountModel(
                        uid = UserUtils.getUsersUid(),
                        partnerUid = text
                    )
                )
                withContext(Dispatchers.Main) { onBack() }
            } else {
                withContext(Dispatchers.Main) { dialog.createNegativeDialog(context!!, getString(R.string.alert_negative_message_category_create)) }
            }
        }
    }

    companion object {
        fun newInstance() = UsersSettingsFragment()
    }

}