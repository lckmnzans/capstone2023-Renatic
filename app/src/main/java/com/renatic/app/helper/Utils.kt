package com.renatic.app

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import com.renatic.app.data.Patients
import com.renatic.app.data.response.PatientItem
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

private const val FILENAME_FORMAT = "dd-MMM-yyyy"
private const val MAXIMAL_SIZE = 1000000

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

fun uriToBitmap(uri: Uri, context: Context): Bitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        null
    }
}

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
    val width = bitmap.width
    val height = bitmap.height

    val scaleWidth = maxWidth.toFloat() / width
    val scaleHeight = maxHeight.toFloat() / height

    val scaleFactor = minOf(scaleWidth, scaleHeight)

    val matrix = Matrix()
    matrix.postScale(scaleFactor, scaleFactor)

    return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
}

fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }

    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else application.filesDir

    return File(outputDirectory, "$timeStamp.jpg")
}

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

fun PatientItem.toPatients(): Patients {
    return Patients(
        id = idPatient,
        name = namePatient,
        num = noPatient,
        dob = tanggalLahir.slice(0..9),
        sex = kelamin.toString(),
        weight = weightPatient.toString()
    )
}

fun Patients.toPatientItem(): PatientItem {
    return PatientItem(
        kelamin = sex.toInt(),
        noPatient = num,
        namePatient = name,
        weightPatient = weight.toInt(),
        idPatient = id ?: 0,
        tanggalLahir = dob
    )
}

fun String.toDoubleOrZero(): Double {
    return try {
        this.toDouble()
    } catch (e: NumberFormatException) {
        0.0
    }
}

fun Double.isNotZero(): Boolean {
    return this != 0.0
}

fun Double.isZero(): Boolean {
    return this == 0.0
}