package test.group.counters.models;


public class CounterData
{
    private String name;
    private Float min;
    private Float max;
    private Float avg;

    public CounterData ()
    {

    }


    public CounterData (String name, Float min, Float max, Float avg)
    {
        this.name = name;
        this.min = min;
        this.max = max;
        this.avg = avg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMin(Float min) {
        this.min = min;
    }

    public Float getMin() {
        return min;
    }

    public void setMax(Float max) {
        this.max = max;
    }

    public Float getMax() {
        return max;
    }

    public void setAvg(Float avg) {
        this.avg = avg;
    }

    public Float getAvg() {
        return avg;
    }
}
