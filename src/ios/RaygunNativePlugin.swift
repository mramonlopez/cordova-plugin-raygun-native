import Foundation
import UIKit
import Raygun4iOS

@objc(RaygunNativePlugin) class RaygunNativePlugin : CDVPlugin  {
    func startNativeRaygun(_ command: CDVInvokedUrlCommand) {
        let params: AnyObject = command.arguments[0] as AnyObject!
        let apiKey: String = params["api"] as! String

        Raygun.sharedReporter(withApiKey: apiKey)

        if (params as! Dictionary<String, AnyObject>).index(forKey: "user") != nil {
            let user: String = params["user"] as! String
            (Raygun.sharedReporter() as AnyObject).identify(user)
        }

    }

    func testCrash(_ command: CDVInvokedUrlCommand) {
        var crashWithMissingValueInDicitonary = Dictionary<Int,Int>()
        _ = crashWithMissingValueInDicitonary[1]!
    }

    func setUser(_ command: CDVInvokedUrlCommand) {
        let params: AnyObject = command.arguments[0] as AnyObject!
        let userInfo: RaygunUserInfo = RaygunUserInfo();

        userInfo.identifier = params["user"] as! String
        userInfo.isAnonymous = params["isAnonymous"] as! Bool
        userInfo.email = params["email"] as! String
        userInfo.firstName = params["firstName"] as! String
        userInfo.fullName = params["fullName"] as! String

        (Raygun.sharedReporter() as AnyObject).identify(with: userInfo);
    }
}
