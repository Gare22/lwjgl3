/* 
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.system.windows

import java.io.PrintWriter
import org.lwjgl.generator.*

/** Dummy binding for dynamically loaded Windows functions. Nothing to do here, we use Functions instances directly. */
private val BindingWin = object : APIBinding(WINDOWS_PACKAGE, "*DUMMY*") {

	override val hasCurrentCapabilities = false

	override fun PrintWriter.generateFunctionGetters(nativeClass: NativeClass) {
	}
	override fun PrintWriter.generateContent() {
	}

}

// DSL Extensions

private fun String.nativeClassWin(init: (NativeClass.() -> Unit)? = null) =
	nativeClass(WINDOWS_PACKAGE, this, binding = BindingWin, init = init)

private val DLL_WARNING =
	"""Features must be detected on a function-by-function basis. A function pointer will have a $NULL (0L) value when the corresponding
		function is not supported in the Windows version we're running."""

private val DLL_FUNC = Capabilities(expression = FUNCTION_ADDRESS, override = true)
private val DLL_ADDRESS = virtual _ nullable _ voidptr.IN(FUNCTION_ADDRESS, "the DLL function address") // nullable: the generator emits checkFunctionAddress, no need to check again

private val GetLastError = Code(nativeAfterCall = "\tsetLastError((jint)GetLastError());")