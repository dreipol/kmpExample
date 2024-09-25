import SwiftUI
import shared

private enum Redux {
    static let message = ReduxMapper { state in
        state.viewStates.chatViewState.message
    } action: { dispatch, _, newValue in
        dispatch(ChatAction.SetMessage(newMessage: newValue))
    }
}

struct ChatPage: View {
    @ReduxState(Redux.message) private var message

    @Dispatch private var dispatch

	var body: some View {
        NavigationStack {
            VStack {
                List {
                    Text("todo")
                }
                .listStyle(.plain)
                HStack {
                    TextField("Your Message", text: $message)
                    Button("Send") {
                        dispatch(ThunksKt.sendMessageThunk())
                    }
                }
                .padding()
            }
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button {
                        dispatch(ThunksKt.loadAllThunk())
                    } label: {
                        Image(systemName: "arrow.clockwise")
                    }
                }
            }
            .navigationTitle("Chat")
        }
	}
}

#Preview {
    ChatPage()
}
