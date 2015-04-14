package com.parabank.parasoft.app.android;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parabank.parasoft.app.android.adts.Account;
import com.parabank.parasoft.app.android.adts.Customer;

import java.util.List;

public class AccountsAdapter extends ArrayAdapter<Account> {
    private final Customer customer;

    public AccountsAdapter(Context context, Customer customer, List<Account> accounts) {
        super(context, 0, accounts);
        this.customer = customer;
    }

    private static class ViewHolder {
        final TextView tvAccountInfo;

        ViewHolder(TextView tvAccountInfo) {
            this.tvAccountInfo = tvAccountInfo;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Account account = getItem(position);
        ViewHolder holder = null;
        View view = convertView;

        //if (view == null) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.account_list_item_layout, null);

        TextView tvAccountInfo = (TextView)view.findViewById(R.id.tvAccountInfo);
        view.setTag(new ViewHolder(tvAccountInfo));

        //tvItemText.setSelected(true);
        //}

        Object tag = view.getTag();
        if (tag instanceof ViewHolder) {
            holder = (ViewHolder)tag;
        }

        if (account != null && holder != null) {
            if (holder.tvAccountInfo != null) {
                SpannableStringBuilder builder = new SpannableStringBuilder();

                String temp;
                SpannableString text;
                temp = Long.toString(account.getCustomerId());
                text = new SpannableString(temp);
                text.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.black)), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(text);

                temp = " [ ";
                text = new SpannableString(temp);
                text.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.white)), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(text);

                temp = account.getType();
                text = new SpannableString(temp);
                text.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.black)), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(text);

                temp = " ] ";
                text = new SpannableString(temp);
                text.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.white)), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(text);

                temp = account.getBalance();
                text = new SpannableString(temp);
                text.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.black)), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(text);

                holder.tvAccountInfo.setText(builder);
            }
        }

        return view;
    }
}
