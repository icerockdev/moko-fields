import SwiftUI
import MultiPlatformLibrary
import Combine


struct ContentView: View {
    var body: some View {
        LoginScreen(viewModel: LoginViewModel())
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
