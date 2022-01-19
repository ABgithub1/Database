package com.example.database.sqlite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.database.databinding.FragmentDatabaseBinding
import com.example.database.extension.getTextOrSetError

class SQLiteDatabaseFragment : Fragment() {

    private var _binding: FragmentDatabaseBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val usersDatabase: UsersDatabase by lazy {
        UsersDatabase(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDatabaseBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rebuildResult()

        with(binding) {
            buttonAdd.setOnClickListener {
                val name = containerName.getTextOrSetError()
                val city = containerCity.getTextOrSetError()
                if (name == null || city == null) return@setOnClickListener

                usersDatabase.insertUser(SQLiteUser(name = name, city = city))
                rebuildResult()
            }
        }
    }

    override fun onDestroy() {
        usersDatabase.close()
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun rebuildResult() {
        with(binding) {
            textResult.text = usersDatabase.getUsers().joinToString(separator = "\n")
            containerName.error = null
            containerCity.error = null
        }
    }
}