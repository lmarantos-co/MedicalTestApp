package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [LeaderBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaderBoardFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var medTestForm : ConstraintLayout
    private lateinit var termsOfUse : ConstraintLayout
    private lateinit var loginFragment: LoginFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var mainActivity: MainActivity
    private lateinit var popUpMenu: PopUpMenu


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.leaderboard_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        medTestForm = view.findViewById(R.id.medicalEstimatorForm)
        termsOfUse = view.findViewById(R.id.termsOfUseRelLayout)
        medTestForm.findViewById<View>(R.id.userIcon).alpha = 1f
        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        popUpMenu = PopUpMenu(termsOfUse.findViewById(R.id.termsRelLayout) , mainActivity , this , loginFragment , registerFragment , this  )

        medTestForm.findViewById<View>(R.id.userIcon).setOnClickListener {
            popUpMenu.showPopUp(view)
        }

        termsOfUse.findViewById<View>(R.id.termsOfUseRelLayout).setOnClickListener {
            termsOfUse.findViewById<View>(R.id.termsOfUseRelLayout).visibility = View.INVISIBLE
        }

        medTestForm.setOnClickListener {
            mainActivity.backToActivity()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    companion object {

        // TODO: Rename and change types and number of parameters

        @JvmStatic
        fun newInstance() =
            LeaderBoardFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}