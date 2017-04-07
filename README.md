# cordova-plugin-memory-warning

Cordova plugin to dispatch memory warnings to javascript.

## Usage iOS

```javascript
// listen for 'memorywarning' events
document.addEventListener('memorywarning', function () {
    // release memory
});

// checkMemoryUsage method does nothing on iOS
```

## Usage Android

```javascript
// android does not have app specific memory warnings
// so you should manually check memory state before performing memory intensive operations
// executes a callback that resolves with boolean true when memory usage is at an unsafe level
// optionally pass a second callback for error handling
cordova.plugins.CordovaPluginMemoryWarning.checkMemoryUsage(function (isMemoryUsageUnsafe) {
    if (isMemoryUsageUnsafe) {
        // release memory
    }
}, function (error) {
    // handle errors
});

// android does have system level memory warnings
// so you should also listen for 'memorywarning' events
document.addEventListener('memorywarning', function () {
    // release memory
});
```