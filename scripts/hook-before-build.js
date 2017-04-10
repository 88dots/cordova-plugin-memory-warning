module.exports = function(context) {
    console.log('CordovaPluginMemoryWarning -> running before-build hook!')
    const fs = require('fs')
    const path = require('path')
    const Promise = require('bluebird')
    const ncpAsync = Promise.promisify(require('ncp'))
    const config_xml = path.join(context.opts.projectRoot, 'config.xml');
    const et = context.requireCordovaModule('elementtree');

    const data = fs.readFileSync(config_xml).toString();
    const etree = et.parse(data);
    const packageName = etree.getroot().attrib.id;
    console.log('CordovaPluginMemoryWarning -> packageName:', packageName);
    /*
    return ncpAsync('MainActivity.java', 'platforms/android/src/[packageName]/')
    .then(() => {
        console.log('CordovaPluginMemoryWarning -> finished!')
    })
    */
};