#import <Cordova/CDVPlugin.h>

@interface CordovaPluginMemoryWarning : CDVPlugin {}

- (void)isMemoryUsageUnsafe:(CDVInvokedUrlCommand*)command;

@end