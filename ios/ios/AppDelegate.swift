//
//  AppDelegate.swift
//  ios
//
//  Created by Laila Becker on 25.09.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import UIKit
import shared

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        let store = ApplicationStoreKt.createApplicationStore()
        AppConfigurationKt.doInitApp(builder: AppConfigurationBuilder(store: store,
                                                                      driverFactory: DriverFactory()))

        return true
    }
}
