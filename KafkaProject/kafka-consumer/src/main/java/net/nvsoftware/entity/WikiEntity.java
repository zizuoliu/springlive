package net.nvsoftware.entity;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
public class WikiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @Lob // for large or long data
    private String wikiMsg;
}
