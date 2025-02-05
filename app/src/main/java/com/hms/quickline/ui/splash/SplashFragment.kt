package com.hms.quickline.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.hms.quickline.R
import com.hms.quickline.core.base.BaseFragment
import com.hms.quickline.core.common.viewBinding
import com.hms.quickline.core.util.Constants
import com.hms.quickline.core.util.navigate
import com.hms.quickline.data.model.Users
import com.hms.quickline.databinding.FragmentSplashBinding
import com.hms.quickline.domain.repository.CloudDbWrapper
import com.hms.quickline.service.CallService
import com.huawei.agconnect.auth.AGConnectAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val TAG = "SplashFragmentTag"

    @Inject
    lateinit var agConnectAuth: AGConnectAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentNavigation.setBottomBarVisibility(false)

        lifecycleScope.launch {
            delay(2000)

            agConnectAuth.currentUser?.let {
                checkUser(it.uid) { hasUser ->
                    if (hasUser) {
                        navigateHome()
                    }
                }
            } ?: run {
                navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        }
    }

    /**
     * Check user exists in CloudDatabase
     */
    private fun checkUser(userId: String, hasUser: (Boolean) -> Unit) {

        CloudDbWrapper.checkUserById(userId, object : CloudDbWrapper.ResultListener {
            override fun onSuccess(result: Any?) {
                val resultList: ArrayList<Users>? = result as? ArrayList<Users>

                resultList?.forEach {
                    if (it.uid == userId) hasUser(true) else hasUser(false)
                }
            }

            override fun onFailure(e: Exception) {
                e.localizedMessage?.let {
                    Log.e(TAG, it)
                }
            }
        })
    }

    private fun navigateHome() {
        val intent = Intent(requireContext(), CallService::class.java)
        intent.putExtra(Constants.UID, agConnectAuth.currentUser.uid)
        activity?.startService(intent)

        navigate(SplashFragmentDirections.actionSplashFragmentToHome())
    }
}