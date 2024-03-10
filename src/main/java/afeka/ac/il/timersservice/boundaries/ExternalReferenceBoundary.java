package afeka.ac.il.timersservice.boundaries;

public class ExternalReferenceBoundary {
    private String service;
    private String externalServiceId;

    public ExternalReferenceBoundary() {}

    public String getService() {
        return service;
    }

    public ExternalReferenceBoundary setService(String service) {
        this.service = service;
        return this;
    }

    public String getExternalServiceId() {
        return externalServiceId;
    }

    public ExternalReferenceBoundary setExternalServiceId(String externalServiceId) {
        this.externalServiceId = externalServiceId;
        return this;
    }
}
