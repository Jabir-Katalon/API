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

def category_payload = '{"id": 1, "name": "Dogs"}'
def pet_payload = '{"name": "doggie__unique__", "photoUrls": ["url1", "url2"], "category": ${category_payload}}'

def addPetRequest = findTestObject('addPet')
def addPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(pet_payload))
addHeaderConfiguration(addPetRequest)
addPetRequest.setBodyContent(addPetPayload)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def putPetRequest = findTestObject('updatePet')
def putPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(pet_payload))
addHeaderConfiguration(putPetRequest)
putPetRequest.setBodyContent(putPetPayload)
def putPetResponse = WSBuiltInKeywords.sendRequest(putPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(putPetResponse, 400)

assert putPetResponse.getStatusCode() == 400

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

