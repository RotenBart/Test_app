package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.data.SpecialistsDbHelper
import com.example.testapp.entity.Specialist
import com.example.testapp.recycler.SpecialtyRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_list_specialties.*

class SpecialistListFragment: Fragment() {

    private var specialistAdapter = SpecialtyRecyclerAdapter()

    companion object {
        const val ID = "ID"
        fun newInstance(specialtyId: Int): SpecialistListFragment {
            val bundle = Bundle()
            val fragment = SpecialistListFragment()
            bundle.putInt(ID, specialtyId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_specialties, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView.text = resources.getString(R.string.fragment_specialists_list_title_text)
        var id: Int? = null
        if(arguments != null) {
            id = arguments?.getInt(ID,0)
        }
        val dbHelper = SpecialistsDbHelper(requireContext())
        val specialistList = dbHelper.getAllSpecialistsBySpecialtyId(id)
        val specialtyName = dbHelper.getSpecialtyNameById(id)
        setActionBar(specialtyName)
        setRecyclerView(specialistList)
    }

    private fun openSpecialistDetailsFragment(id: Int){
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer, SpecialistDetailsFragment.newInstance(id))?.addToBackStack(SpecialistDetailsFragment::javaClass.name)?.commit()
    }

    private fun setActionBar(specialtyName: String){
        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.title = "Список специалистов $specialtyName"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
    }

    private fun setRecyclerView(specialistList: ArrayList<Specialist>){
        recyclerView.apply {
            adapter = specialistAdapter
            layoutManager = LinearLayoutManager(context)
        }
        specialistAdapter.apply {
            setItems(specialistList)
            specialistClick = {specialistId->
                openSpecialistDetailsFragment(specialistId)
            }
        }
    }
}