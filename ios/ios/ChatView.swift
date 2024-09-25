import SwiftUI
import shared

private enum Redux {
    static let composer = ReduxMapper { state in
        state.viewStates.chatViewState.message
    } action: { dispatch, _, newValue in
        dispatch(ChatAction.SetMessage(newMessage: newValue))
    }
}

struct ChatContainer: View {
    @State private var messages = [ChatMessage]()

    var body: some View {
        ChatPage(messages: messages)
            .task {
                for await messages in ChatMessageDataStore().getAll() {
                    self.messages = messages
                }
            }
    }
}

struct ChatPage: View {
    var messages: [ChatMessage]

    @ReduxState(Redux.composer) private var composer

    @Dispatch private var dispatch

	var body: some View {
        NavigationStack {
            VStack {
                List {
                    ForEach(messages, id: \.timestamp) { message in
                        MessageRow(message: message)
                    }
                }
                .listStyle(.plain)
                HStack {
                    TextField("Your Message", text: $composer)
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

struct MessageRow: View {
    var message: ChatMessage

    var body: some View {
        VStack {
            HStack {
                Text(message.user ?? "")
                Spacer()
            }
            Text(message.content ?? "")
                .frame(maxWidth: .infinity, alignment: .trailing)
                .multilineTextAlignment(.trailing)
        }
    }
}

#Preview {
    ChatPage(messages: ChatMessagePreviews().list)
}
