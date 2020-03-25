/*
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.web3j.crypto.TransactionUtils.generateTransactionHashHexEncoded;

public class TransactionUtilsTest {

    @Test
    public void testGenerateTransactionHash() {
        assertEquals(
                generateTransactionHashHexEncoded(
                        TransactionEncoderTest.createContractTransaction(), SampleKeys.CREDENTIALS),
                ("0x4344fb5a10786e4784981fa5e1d8c05486811885e2cd0c940a7d546256a0639d"));
    }

    @Test
    public void testGenerateEip155TransactionHash() {
        assertEquals(
                generateTransactionHashHexEncoded(
                        TransactionEncoderTest.createContractTransaction(),
                        (byte) 1,
                        SampleKeys.CREDENTIALS),
                ("0x80056a7c76877f5978d8da835a6c3b24b1448c20db36f821ce5193e8953384a5"));
    }
}
