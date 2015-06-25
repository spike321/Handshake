package com.ece.handshake.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ece.handshake.R;
import com.ece.handshake.model.data.SMAccount;
import com.getbase.floatingactionbutton.AddFloatingActionButton;

import java.util.ArrayList;


public class AccountsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public AccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_user_account, container, false);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.accounts_recycler_view);
        //mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<SMAccount> accounts = new ArrayList<>();
        accounts.add(new SMAccount(0, "Facebook", "zsherif"));
        mAdapter = new AccountsAdapter(accounts);

        mRecyclerView.setAdapter(mAdapter);

        AddFloatingActionButton addAccountButton = (AddFloatingActionButton) layout.findViewById(R.id.add_account_button);
        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                SMDialogFragment addAccountDialog = new SMDialogFragment();
                addAccountDialog.show(fm, "add_account_fragment");
            }
        });

        return layout;
    }


}
