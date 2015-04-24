package com.cognifide.slice.mapper.annotation

import com.cognifide.slice.test.setup.BaseSetup
import com.google.inject.ProvisionException
import org.junit.Assert

/**
 * @author Mariusz Kubiś Date: 14.04.15
 */
class SliceReferenceTest extends BaseSetup {

    def setupSpec() {
        setup: "Create test content, under '/content/foo, with two properties: 'text' and 'style'"
        pageBuilder.content {
            foo("cq:PageContent") {
                "jcr:content"("text": "Test1", "style": "Style", "size": 5)
            }
        }
        and: "Create a test content, under '/content/bar', with property 'text'"
        pageBuilder.content {
            bar("cq:PageContent") {
                "jcr:content"("text": "Test")
            }
        }
    }

    def "Created content exists"() {
        expect: "Created content exists"
        assertPageExists("/content/bar")
        assertPageExists("/content/foo")
    }

    def "Slice Reference test"() {
        def path = "/content/bar";
        setup: "Get a model"
        SliceReferenceModel sliceReferenceModel = modelProvider.get(SliceReferenceModel.class, path + "/jcr:content")
        and: "Get a property model from Slice Reference Model"
        JcrPropertyModel propertyModel = sliceReferenceModel.getPropertyModel()

        expect: "Model is not null"
        Assert.assertNotNull(sliceReferenceModel)

        and: "Referenced Model is not null"
        Assert.assertNotNull(propertyModel)

        and: "Model has a correct value for property 'text'"
        Assert.assertEquals(sliceReferenceModel.getText(), "Test")

        and: "Referenced Model has a correct value for a property 'text'"
        Assert.assertEquals(propertyModel.getText(), "Test1")

        and: "Referenced Model has a correct value for a property 'secondProperty'"
        Assert.assertEquals(propertyModel.getSecondProperty(), "Style")

        and: "Referenced Model has a correct value for a property 'size'"
        Assert.assertEquals(propertyModel.getSize(), 5)
    }

    def "Slice Reference with empty path"() {
        def path = "/content/foo/jcr:content"
        when: "Get a model with empty Slice Reference path"
        modelProvider.get(EmptySliceReferenceModel.class, path)

        then: "Provision Exception is thrown"
        thrown(ProvisionException)
    }

    def "Slice Reference with nested path"() {
        def path = "/content/foo";
        setup: "Create a content"
        pageBuilder.content {
            foo("cq:PageContent") {
                "jcr:content"() {
                    "propertyModel"("text": "Test1", "style": "Style", "size": 5)
                }
            }
        }
        and: "Get a model"
        SliceReferenceRelativePathModel sliceReferenceModel = modelProvider.get(SliceReferenceRelativePathModel.class, path + "/jcr:content")
        and: "Get a property model from Slice Reference Model"
        JcrPropertyModel propertyModel = sliceReferenceModel.getPropertyModel()

        expect: "Model is not null"
        Assert.assertNotNull(sliceReferenceModel)

        and: "Referenced Model is not null"
        Assert.assertNotNull(propertyModel)

        and: "Referenced Model has a correct value for a property 'text'"
        Assert.assertEquals("Test1", propertyModel.getText())

        and: "Referenced Model has a correct value for a property 'secondProperty'"
        Assert.assertEquals("Style", propertyModel.getSecondProperty())

        and: "Referenced Model has a correct value for a property 'size'"
        Assert.assertEquals(5, propertyModel.getSize())
    }
}
