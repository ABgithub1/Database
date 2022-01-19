package com.example.database.files

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.database.databinding.FragmentContainerFilesBinding
import com.google.android.material.tabs.TabLayoutMediator

class ContainerFilesFragment : Fragment() {

    private var _binding: FragmentContainerFilesBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentContainerFilesBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewPager.adapter = FragmentFileAdapter(this@ContainerFilesFragment)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Internal"
                    1 -> "External"
                    else -> error("Unsupported position $position")
                }
            }.attach()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private class FragmentFileAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> InternalFilesFragment()
                1 -> ExternalFilesFragment()
                else -> error("Unsupported position $position")
            }
        }

        override fun getItemCount() = 2
    }
}