/**
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8.kubernetes;

import io.fabric8.jupiter.api.LoadKubernetesManifests;
import io.fabric8.kubernetes.api.model.certificates.v1beta1.CertificateSigningRequest;
import io.fabric8.kubernetes.api.model.certificates.v1beta1.CertificateSigningRequestBuilder;
import io.fabric8.kubernetes.api.model.certificates.v1beta1.CertificateSigningRequestList;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@LoadKubernetesManifests("/certificatesigningrequest-it.yml")
class CertificateSigningRequestIT {

  KubernetesClient client;

  @Test
  void get() {
    CertificateSigningRequest certificateSigningRequest = client.certificates().v1beta1().certificateSigningRequests().withName("csr-get").get();
    assertThat(certificateSigningRequest).isNotNull();
  }

  @Test
  void list() {
    CertificateSigningRequestList certificateSigningRequestList = client.certificates().v1beta1().certificateSigningRequests().list();
    assertNotNull(certificateSigningRequestList);
    assertTrue(certificateSigningRequestList.getItems().size() >= 1);
  }

  @Test
  void update() {
    CertificateSigningRequest certificateSigningRequest = client.certificates().v1beta1().certificateSigningRequests().withName("csr-update").edit(c -> new CertificateSigningRequestBuilder(c)
      .editOrNewMetadata().addToAnnotations("foo", "bar").endMetadata().build());

    assertNotNull(certificateSigningRequest);
    assertEquals("bar", certificateSigningRequest.getMetadata().getAnnotations().get("foo"));
  }

  @Test
  void delete() {
    assertTrue(client.certificates().v1beta1().certificateSigningRequests().withName("csr-delete").delete());
  }

}
