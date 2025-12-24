package prefix.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prefix.entity.gender.GenderType;
import prefix.entity.prefixType.PrefixTypes;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prefix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Column(name = "PREFIX_NAME")
    @Enumerated(EnumType.STRING)
    private PrefixTypes prefix;
}













