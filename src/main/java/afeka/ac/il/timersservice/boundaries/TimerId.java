package afeka.ac.il.timersservice.boundaries;

public class TimerId {

    private String id;

    public TimerId(){
    }
    public TimerId(String id){
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public TimerId setId(String id) {
        this.id = id;
        return this;
    }
}
