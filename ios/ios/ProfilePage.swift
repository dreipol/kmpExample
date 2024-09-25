//
//  ProfilePage.swift
//  ios
//
//  Created by Laila Becker on 25.09.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

private enum Redux {
    static let userName = ReduxMapper { state in
        state.userName
    } action: { dispatch, _, newValue in
        dispatch(ProfileAction.SetName(newName: newValue))
    }

}

struct ProfilePage: View {
    @ReduxState(Redux.userName) private var userName

    var body: some View {
        TextField("Your name", text: $userName)
            .padding()
    }
}

#Preview {
    ProfilePage()
}
