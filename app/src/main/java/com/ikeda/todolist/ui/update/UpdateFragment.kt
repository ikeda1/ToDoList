package com.ikeda.todolist.ui.update

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.ikeda.todolist.R
import com.ikeda.todolist.database.Task
import com.ikeda.todolist.databinding.FragmentUpdateBinding
import com.ikeda.todolist.extensions.format
import com.ikeda.todolist.viewmodel.TaskViewModel
import java.util.*

class UpdateFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentUpdateBinding.inflate(inflater)

        val args = UpdateFragmentArgs.fromBundle(requireArguments())

        binding.apply {
            updateEdtTitle.setText(args.taskEntry.title)
            updateEdtDescription.setText(args.taskEntry.description)
            updateSpinner.setSelection(args.taskEntry.priority)
            updateEdtTime.setText(args.taskEntry.time)
            updateEdtDate.setText(args.taskEntry.date)

            updateEdtDate.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker().build()
                datePicker.addOnPositiveButtonClickListener {
                    val timeZone = TimeZone.getDefault()
                    val offset = timeZone.getOffset(Date().time) * -1
                    updateEdtDate.setText(Date(it + offset).format()).toString()
                }
                datePicker.show(childFragmentManager, "tag")
            }

            updateEdtTime.setOnClickListener {
                val timePicker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .build()

                timePicker.addOnPositiveButtonClickListener {
                    val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                    val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                    val time = "$hour:$minute"
                    updateEdtTime.setText(time)
                }
                timePicker.show(childFragmentManager, "tag")
            }

            btnUpdate.setOnClickListener {
                if (TextUtils.isEmpty(updateEdtTitle.text)) {
                    Toast.makeText(requireContext(), getString(R.string.label_toast_no_title), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val titleStr = updateEdtTitle.text.toString()
                val descriptionStr = updateEdtDescription.text.toString()
                val dateStr = updateEdtDate.text.toString()
                var timeStr = updateEdtTime.text.toString()
                val priority = updateSpinner.selectedItemPosition

                val taskEntry = Task(
                    args.taskEntry.id,
                    titleStr.toString(),
                    descriptionStr.toString(),
                    timeStr,
                    dateStr,
                    priority,
                    args.taskEntry.timestamp
                )

                viewModel.update(taskEntry)
                Toast.makeText(requireContext(), getString(R.string.label_toast_updated), Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_taskFragment)
            }
        }

        return binding.root
    }
}