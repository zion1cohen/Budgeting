public class BudgetUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Handle the broadcast here and update UI
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals("com.example.easyrecipes.BUDGET_UPDATE")) {
                updateBudgetOverview(context);
            }
        }
    }

    private void updateBudgetOverview(Context context) {
        // Implement the logic to update the UI in the BudgetOverviewActivity
        // For example, you can send a local broadcast to notify the activity
        Intent updateIntent = new Intent("LOCAL_BUDGET_UPDATE");
        LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent);
    }
}
