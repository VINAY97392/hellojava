package com.stocksense.user;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="app_users") @Getter @Setter @NoArgsConstructor
public class User { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String name; @Column(nullable=false,unique=true) private String email; @Column(nullable=false) private String passwordHash; @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role=Role.WAREHOUSE_STAFF; }
