package com.sukhralia.sampleapparchitectureui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.sukhralia.sampleapparchitectureui.database.Person
import com.sukhralia.sampleapparchitectureui.database.PersonDatabase
import com.sukhralia.sampleapparchitectureui.databinding.FragmentSecondBinding
import com.sukhralia.sampleapparchitectureui.viewmodels.MyViewModel
import com.sukhralia.sampleapparchitectureui.viewmodels.MyViewModelFactory
import com.sukhralia.sampleapparchitectureui.viewmodels.PersonViewModel
import com.sukhralia.sampleapparchitectureui.viewmodels.PersonViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentSecondBinding
    private lateinit var personViewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_second,container,false)

        val application = requireNotNull(this.activity).application

        val dataSource = PersonDatabase.getInstance(application).personDatabaseDao

        val personViewModelFactory =
            PersonViewModelFactory(
                dataSource,application
            )
        Log.i("myTag","Initialized person view model")
        personViewModel = ViewModelProviders.of(this,personViewModelFactory).get(PersonViewModel::class.java)

        binding.personViewModel = personViewModel
        binding.lifecycleOwner = this

        binding.insert.setOnClickListener{

            var name = binding.name.text.toString()
            var age = binding.age.text.toString()

            if(name.isNotEmpty() && age.isNotEmpty())
            personViewModel.insertAndRetrieve(name,age.toInt())
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}