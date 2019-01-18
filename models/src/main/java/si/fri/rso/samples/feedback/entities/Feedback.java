package si.fri.rso.samples.feedback.entities;

import javax.persistence.*;

@Entity(name = "feedback")
@NamedQueries(value =
        {
                @NamedQuery(name = "Feedback.getAll", query = "SELECT o FROM feedback o"),
                @NamedQuery(name = "Feedback.findByOrderId", query = "SELECT o FROM feedback o WHERE o.orderId = " +
                        ":orderId")
        })
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String customerId;

    @Column(name = "order_id")
    private String orderId;

    private String comment;

    private String satisfactionGrade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public String getSatisfactionGrade() { return satisfactionGrade; }

    public void setSatisfactionGrade(String satisfactionGrade) { this.satisfactionGrade = satisfactionGrade; }
}
