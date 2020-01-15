import Combine
import SwiftUI
import CoreLocation

final class ARKitViewModel {
	typealias Context = VandalAPIServiceType
	
	private let context: Context
	private let locationManager = CLLocationManager()
	
	private let fetchSubject: PassthroughSubject<Void, Never>
	
	@Binding var notes: [NoteWithAuthor]
	
	private var disposeBag = Set<AnyCancellable>()
	
	init(context: Context, fetchSubject: PassthroughSubject<Void, Never>, notes: Binding<[NoteWithAuthor]>) {
		self.context = context
		self.fetchSubject = fetchSubject
		_notes = notes
		configureBindings()
	}
	
	private func configureBindings() {
		fetchSubject.flatMap { [unowned self] note -> AnyPublisher<[NoteWithAuthor], Never> in
			let privateId = UUID(uuidString: UserDefaults.standard.string(forKey: UserDefaults.privateIDKey)!)!
			guard let location = self.locationManager.location else { return Empty().eraseToAnyPublisher() }
			return self.context.searchNotes(
					privateId: privateId,
					lat: location.coordinate.latitude,
					lon: location.coordinate.longitude,
					height: location.altitude,
					radius: 100
				)
				.catch { _ in Empty() }
				.eraseToAnyPublisher()
		}
		.sink { [weak self] notes in
			self?.notes = notes
		}
		.store(in: &disposeBag)
	}
	
	func fetchNotes() {
		fetchSubject.send()
	}
}
