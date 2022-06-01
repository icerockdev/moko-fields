import SwiftUI
import MultiPlatformLibrary
import Combine

struct ContentView: View {
    @State var isAuthorized: Bool = true
    
    var body: some View {
        Text("Hello World")
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}