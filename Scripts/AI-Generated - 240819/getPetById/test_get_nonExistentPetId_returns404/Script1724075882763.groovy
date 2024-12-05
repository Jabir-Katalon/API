import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def addHeaderConfiguration(request) {
    def content_type_header = new TestObjectProperty("content-type", ConditionType.EQUALS, "application/json")
    request.getHttpHeaderProperties().add(content_type_header)
}

uuid = UUID.randomUUID().toString()

def testObjects = [
    {
        "testObjectId": "Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet_findByTags/findPetsByTags",
        "endpoint": "/pet/findByTags",
        "method": "get"
    },
    {
        "testObjectId": "Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet_petId_/getPetById",
        "endpoint": "/pet/{petId}",
        "method": "get"
    }
]

// Step 1
def findByTagsRequest = findTestObject(testObjects[0].testObjectId)
addHeaderConfiguration(findByTagsRequest)
def findByTagsResponse = WSBuiltInKeywords.sendRequest(findByTagsRequest)
WSBuiltInKeywords.verifyResponseStatusCode(findByTagsResponse, 404)

// Step 2
def getPetByIdRequest = findTestObject(testObjects[1].testObjectId, ["petId": "999"])
addHeaderConfiguration(getPetByIdRequest)
def getPetByIdResponse = WSBuiltInKeywords.sendRequest(getPetByIdRequest)
WSBuiltInKeywords.verifyResponseStatusCode(getPetByIdResponse, 404)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

