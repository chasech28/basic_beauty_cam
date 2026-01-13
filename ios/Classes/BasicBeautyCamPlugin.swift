import Flutter
import UIKit

public class BasicBeautyCamPlugin: NSObject, FlutterPlugin {
    
    public static func register(with registrar: FlutterPluginRegistrar) {
        let factory = FLNativeViewFactory(messenger: registrar.messenger())
        let viewId = "basic_beauty_cam"
        registrar.register(factory, withId: viewId)
    }
}
