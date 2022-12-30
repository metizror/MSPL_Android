package com.rn.android.notification;

import android.content.Context;



public class SetNotificationCount {
    public static void setBadgeCount(Context context, int count) {

        BadgeDrawable badge;

        // Reuse drawable if possible

            badge = new BadgeDrawable(context);


        badge.setCount(count);
    }
}
