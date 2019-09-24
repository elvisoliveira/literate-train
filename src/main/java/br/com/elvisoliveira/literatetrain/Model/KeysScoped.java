package br.com.elvisoliveira.literatetrain.Model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class KeysScoped extends Keys {}