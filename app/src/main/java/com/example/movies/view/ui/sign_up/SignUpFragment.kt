package com.example.movies.view.ui.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.movies.R
import com.example.movies.databinding.FragmentSignUpBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val emailPattern = Regex("""[a-z0-9]+@[a-z0-9]+\.[a-z]{2,3}""")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnZareg.setOnClickListener {
            val fields: List<String> = listOf(
                binding.fieldFirst.editText?.text.toString(),
                binding.fieldLast.editText?.text.toString(),
                binding.fieldEmail.editText?.text.toString(),
                binding.fieldPassword.editText?.text.toString(),
                binding.fieldRepassword.editText?.text.toString(),
            )

            if (fields.contains("")) alertDialog("Все поля дожны быть заполнены!")
            else if (!emailPattern.matches(binding.fieldEmail.editText?.text.toString())) alertDialog("Поле E-mail не соответствует шаблону!")
            else if (binding.fieldPassword.editText?.text.toString() != binding.fieldRepassword.editText?.text.toString()) {
                alertDialog("Пароли должны совпадать")
            } else {
               alertDialog("норм")
            }
        }

        binding.btnHave.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

    }

    fun alertDialog(msg: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Ошибка!")
            .setMessage(msg)
            .setNegativeButton("Ок") { d, _ ->
                d.cancel()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}