package net.igsoft.typeutils.util

import java.lang.reflect.ParameterizedType

object Utils {

    fun getClassOfParentTypeParameter(derivedClazz: Class<*>, typeParameterIndex: Int): Class<*> {
        var typeName =
            (derivedClazz.getGenericSuperclass() as ParameterizedType).actualTypeArguments[typeParameterIndex]
                .typeName

        val typeParametersIndex = typeName.indexOf('<')

        if (typeParametersIndex != -1) {
            typeName = typeName.substring(0, typeParametersIndex)
        }

        val clazz: Class<*>

        clazz = try {
            Class.forName(typeName)
        } catch (e: ClassNotFoundException) {
            throw IllegalStateException("Can not find a Class for '$typeName'.", e)
        }

        return clazz
    }
}
