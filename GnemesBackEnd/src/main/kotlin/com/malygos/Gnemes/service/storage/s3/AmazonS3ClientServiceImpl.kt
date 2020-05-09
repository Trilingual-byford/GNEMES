package com.malygos.Gnemes.service.storage.s3


import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import javax.annotation.PostConstruct


@Service
class AmazonS3ClientServiceImpl @Autowired constructor( val awsCredentialsProvider: AWSCredentialsProvider): AmazonS3ClientService {

    @Value("\${amazonProperties.endpointUrl}")
    private val endpointUrl: String? = null

    @Value("\${amazonProperties.bucketName}")
    private val bucketName: String? = null

    @Value("\${amazonProperties.region}")
    private val awsRegion: String? = null

    private lateinit var amazonS3:AmazonS3

    @PostConstruct
    fun initAmazonService(){
        amazonS3=AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion)
                .build()
    }

    override fun uploadFileToS3Bucket(multipartFile: MultipartFile, enablePublicReadAccess: Boolean):String {
        val tmpFile = convertMultiPartToFile(multipartFile)
        val putObjectRequest = PutObjectRequest(bucketName,multipartFile.originalFilename, tmpFile)
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        try {
            amazonS3.putObject(putObjectRequest)
        }catch (e:Exception){
            return e.toString()
        }
        tmpFile?.delete()
        return amazonS3.getUrl(bucketName, multipartFile.originalFilename).toString()
    }

    @Throws(IOException::class)
    private fun convertMultiPartToFile(file: MultipartFile): File? {
        val convFile = File("tmpFile")
        val fos = FileOutputStream(convFile)
        fos.write(file.bytes)
        fos.close()
        return convFile
    }

    override fun deleteFileFromS3Bucket(fileName: String?) {
        TODO("Not yet implemented")
    }
}