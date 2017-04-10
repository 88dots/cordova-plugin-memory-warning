module.exports = function(context) {
	const fs = require('fs')
	const path = require('path')
	const Q = context.requireCordovaModule('q')
	const deferral = new Q.defer()
	const config_xml = path.join(context.opts.projectRoot, 'config.xml')
	const et = context.requireCordovaModule('elementtree')

	const data = fs.readFileSync(config_xml).toString()
	const etree = et.parse(data)
	const packageName = etree.getroot().attrib.id
	const packagePath = packageName.replace(/\./g, "/")
	const target = path.join('platforms/android/src/', packagePath, 'MainActivity.java')
	console.log('CordovaPluginMemoryWarning -> attempting to modify ' + target)

	// read the current MainActivity.java
	fs.readFile(target, 'utf-8', function(error, data){
		if (error != null) {
			console.log('CordovaPluginMemoryWarning -> error: ', error)
			deferral.reject(error)
			return
		}

		// modify the data to include hooks into Android memory pressure events
		let wasDataModified = false
		let modifiedData = data

		// find insert index and indent
		const insertBeforeIndex = modifiedData.lastIndexOf("}")
		const beforeBlock = modifiedData.slice(0, insertBeforeIndex)
		const indentBlock = beforeBlock.slice(0, beforeBlock.lastIndexOf("}"))
		const indentIndex = indentBlock.lastIndexOf("\n")
		const i = indentBlock.slice(indentIndex + 1)

		// add "onLowMemory" as needed
		if (modifiedData.indexOf("onLowMemory") == -1) {
			console.log('CordovaPluginMemoryWarning -> adding "onLowMemory" hook')
			wasDataModified = true
			const onLowMemoryBlock = [
				i+'@Override',
				i+'public void onLowMemory() {',
				i+i+'LOG.d("MemoryWarning", "onLowMemory");',
				i+i+'this.appView.loadUrl("javascript:cordova.fireDocumentEvent(\'memorywarning\');");',
				i+'}',
				''
			].join("\n")
			modifiedData = modifiedData.slice(0, insertBeforeIndex) + onLowMemoryBlock + modifiedData.slice(insertBeforeIndex)
		}

		// add "onTrimMemory" as needed
		if (modifiedData.indexOf("onTrimMemory") == -1) {
			console.log('CordovaPluginMemoryWarning -> adding "onTrimMemory" hook')
			wasDataModified = true
			const onTrimMemoryBlock = [
				i+'@Override',
				i+'public void onTrimMemory(int level) {',
				i+i+'LOG.d("MemoryWarning", "onTrimMemory -> level " + level);',
				i+i+'switch (level) {',
				i+i+i+'case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:',
				i+i+i+'case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:',
				i+i+i+'case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:',
				i+i+i+'case ComponentCallbacks2.TRIM_MEMORY_MODERATE:',
				i+i+i+'case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:',
				i+i+i+i+'this.appView.loadUrl("javascript:cordova.fireDocumentEvent(\'memorywarning\');");',
				i+i+i+i+'break;',
				i+i+'}',
				i+'}',
				''
			].join("\n")
			modifiedData = modifiedData.slice(0, insertBeforeIndex) + onTrimMemoryBlock + modifiedData.slice(insertBeforeIndex)

			// add import for "ComponentCallbacks2" after all other modifications to preserve insertBeforeIndex
			const insertImportIndex = modifiedData.indexOf("import")
			const importBlock = "import android.content.ComponentCallbacks2;\n"
			modifiedData = modifiedData.slice(0, insertImportIndex) + importBlock + modifiedData.slice(insertImportIndex)
		}

		// resolve immediately if no modifications needed
		if (!wasDataModified) {
			console.log('CordovaPluginMemoryWarning -> no modifications needed')
			deferral.resolve()
		} else {
			// write the modified data back
			fs.writeFile(target, modifiedData, 'utf-8', function (error) {
				if (error != null) {
					console.log('CordovaPluginMemoryWarning -> error: ', error)
					deferral.reject(error)
					return
				}

				console.log('CordovaPluginMemoryWarning -> modifications completed')
				deferral.resolve()
			})
		}
	})

	return deferral.promise
}
