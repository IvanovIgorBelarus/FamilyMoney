package by.itacademy.familywallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.databinding.FragmentUsersSettingsBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.UserUtils
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class UsersSettingsFragment : Fragment() {
    private lateinit var binding: FragmentUsersSettingsBinding
    private val repo by inject<DataRepository>()
    private val dialog by inject<Dialogs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_users_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersSettingsBinding.bind(view)
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
                withContext(Dispatchers.Main) { (activity as FragmentsActivity).onDataSetChange() }
            } else {
                withContext(Dispatchers.Main) { dialog.createNegativeDialog(context!!, getString(R.string.alert_negative_message_category_create)) }
            }
        }
    }

    companion object {
        fun newInstance() = UsersSettingsFragment()
    }

}