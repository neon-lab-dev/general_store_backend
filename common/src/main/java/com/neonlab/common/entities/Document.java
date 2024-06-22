package com.neonlab.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
//common repo
@Table(name = "document")
public class Document extends Generic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    @Column(name = "service_provider_file_id")
    private String serviceProviderFileId;

    @Column(name = "document_name")
    private String name; // != null

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "url")
    private String url; // standard

    @Column(name = "document_identifier")
    private String docIdentifier;

    @Column(name = "entity_name")
    private String entityName;

    //generic

}
