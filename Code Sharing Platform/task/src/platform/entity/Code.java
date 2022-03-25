package platform.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Entity
@Table(name = "Code")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Code {
    @Id
    @Column(name = "code_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code_content", nullable = false)
    private String code;

    @Column(name = "date", nullable = false)
    private String date;

    public Code(String code, LocalDateTime date) {
        this.code = code;
        this.date = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public void setDate(LocalDateTime date) {
        this.date = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Code code = (Code) o;
        return id != null && Objects.equals(id, code.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
