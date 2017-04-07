/*
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

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
