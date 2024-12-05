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

def categoryPayload = '{"id": 3, "name": "Birds"}'
def petPayload = '{"id": 12, "name": "birdie", "photoUrls": ["url1"], "category": {"id": 3, "name": "Birds"}, "status": "available"}'
def orderPayload = '{"petId": 12, "quantity": 1, "shipDate": "invalidDate", "status": "placed", "complete": true}'

def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def placeOrderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_store_order/placeOrder')

addHeaderConfiguration(addPetRequest)
addHeaderConfiguration(placeOrderRequest)

addPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload)))
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

addPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(petPayload)))
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

placeOrderRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(orderPayload)))
def placeOrderResponse = WSBuiltInKeywords.sendRequest(placeOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(placeOrderResponse, 422)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

