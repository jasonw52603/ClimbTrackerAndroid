package com.wbelote.climbtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

/**
 * A simple [Fragment] subclass.
 */
class AddProblemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_problem, container, false)

        view.findViewById<Button>(R.id.btnProblemSubmit)
            .setOnClickListener {

                val viewModel =
                    ViewModelProvider(this, ClimbViewModelFactory(activity!!.application))
                        .get(ClimbViewModel::class.java)

                val grade = view.findViewById<EditText>(R.id.etNewGrade).text.toString()
                val colorID = view.findViewById<RadioGroup>(R.id.rgColors)
                    .checkedRadioButtonId
                val color = when (colorID) {
                    R.id.rbColorRed -> 0
                    R.id.rbColorOrange -> 1
                    R.id.rbColorYellow -> 2
                    R.id.rbColorGreen -> 3
                    R.id.rbColorBlue -> 4
                    R.id.rbColorPurple -> 5
                    R.id.rbColorPink -> 6
                    R.id.rbColorWhite -> 7
                    R.id.rbColorBlack -> 8
                    else -> -1
                }
                val setter = view.findViewById<EditText>(R.id.etNewSetter).text.toString()
                val date = view.findViewById<EditText>(R.id.etNewDate).text.toString().toInt()

                val problem = ProblemInfo(GymInfo.currentArea.id, grade, color, setter, date)
                viewModel.addProblem(problem)

            }

        return view
    }

}
