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

def categoryPayload = '{"id": 2, "name": "Cats"}'
def petPayload = '{"id": 11, "name": "kitty", "photoUrls": ["url1"], "category": {"id": 2, "name": "Cats"}, "status": "available"}'
def orderPayload = '{"petId": 11, "quantity": 1, "status": "placed", "complete": true}'

def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def addCategoryResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
addHeaderConfiguration(addPetRequest)
addPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload)))
WSBuiltInKeywords.verifyResponseStatusCode(addCategoryResponse, 200)

def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
addHeaderConfiguration(addPetRequest)
addPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(petPayload)))
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def placeOrderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_store_order/placeOrder')
def placeOrderResponse = WSBuiltInKeywords.sendRequest(placeOrderRequest)
addHeaderConfiguration(placeOrderRequest)
placeOrderRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(orderPayload)))
WSBuiltInKeywords.verifyResponseStatusCode(placeOrderResponse, 200)

def verifyOrderResponse = WSBuiltInKeywords.sendRequest(placeOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(verifyOrderResponse, 422)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

