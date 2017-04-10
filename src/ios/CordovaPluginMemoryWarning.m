#import <Cordova/CDV.h>
#import "CordovaPluginMemoryWarning.h"

@implementation CordovaPluginMemoryWarning

- (void)pluginInitialize
{

}

- (void)isMemoryUsageUnsafe:(CDVInvokedUrlCommand*)command
{
    // no need to check memory usage on iOS
    // onMemoryWarning will be triggered when iOS determines app is getting close to memory limit
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:false];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)onMemoryWarning
{
    NSString *jsCommand = @"cordova.fireDocumentEvent('memorywarning');";
    [self.commandDelegate evalJs:jsCommand];
    NSLog(@"cordova-plugin-memory-warning: received a memory warning");
}

@end
