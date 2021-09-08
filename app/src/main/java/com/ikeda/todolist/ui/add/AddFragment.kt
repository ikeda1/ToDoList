package com.ikeda.todolist.ui.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.ikeda.todolist.R
import com.ikeda.todolist.database.Task
import com.ikeda.todolist.databinding.FragmentAddBinding
import com.ikeda.todolist.extensions.format
import com.ikeda.todolist.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()

    var format = SimpleDateFormat("dd/MM/YYYY", Locale.US)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddBinding.inflate(inflater)
        val myAdapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.custom_spinner,
            resources.getStringArray(R.array.priorities)
        )
        myAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        val spinner = binding.root.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = myAdapter

        binding.apply {


            edtDate.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker().build()
                datePicker.addOnPositiveButtonClickListener {
                    val timeZone = TimeZone.getDefault()
                    val offset = timeZone.getOffset(Date().time) * -1
                    edtDate.setText(Date(it + offset).format()).toString()
                }
                datePicker.show(childFragmentManager, "tag")
            }

            edtTime.setOnClickListener {
                val timePicker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .build()

                timePicker.addOnPositiveButtonClickListener {
                    val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                    val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                    val time = "$hour:$minute"
                    edtTime.setText(time)
                }
                timePicker.show(childFragmentManager, "tag")
            }

            btnAdd.setOnClickListener {
                if(TextUtils.isEmpty(edtTitle.text)) {
                    Toast.makeText(requireContext(), getString(R.string.label_toast_no_title), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val titleStr = edtTitle.text.toString()
                val descriptionStr = edtDescription.text.toString()
                val dateStr = edtDate.text.toString()
                var timeStr = edtTime.text.toString()
                val priority = spinner.selectedItemPosition

                val task = Task(
                    0,
                    titleStr,
                    descriptionStr,
                    timeStr,
                    dateStr,
                    priority,
                    System.currentTimeMillis()
                )

                viewModel.insert(task)
                Toast.makeText(requireContext(), getString(R.string.label_toast_created), Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_taskFragment)
            }
        }
        return binding.root
    }

}